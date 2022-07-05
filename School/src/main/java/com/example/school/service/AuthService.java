package com.example.school.service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.school.constants.Constants;
import com.example.school.dto.AuthTokenDO;
import com.example.school.dto.CreateAccountDO;
import com.example.school.dto.LoginDO;
import com.example.school.dto.NewTokenDO;
import com.example.school.dto.UserDO;
import com.example.school.dto.UserDOmini;
import com.example.school.exception.InvalidInputsExceptions;
import com.example.school.exception.InvalidTokenException;
import com.example.school.exception.SchoolException;
import com.example.school.exception.UnauthorizedUserException;
import com.example.school.exception.UserDisabledException;
import com.example.school.exception.UserNotFoundException;
import com.example.school.model.Address;
import com.example.school.model.NewUserToken;
import com.example.school.model.Role;
import com.example.school.model.Token;
import com.example.school.model.User;
import com.example.school.repository.AddressRepository;
import com.example.school.repository.NewUserTokenRepository;
import com.example.school.repository.RoleRepository;
import com.example.school.repository.TokenRepository;
import com.example.school.repository.UserRepository;
import com.example.school.security.JwtHelper;
import com.example.school.util.CommonBeans;
import com.example.school.util.RandomGenerator;

import antlr.debug.NewLineEvent;

@Service
public class AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtHelper jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenRepository tokenRepository;

	@Value("${jwt.expiration.hours}")
	private long jwtExpirationHours;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NewUserTokenRepository newUserTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Value("${signup.token.expiration.hours}")
	private long signUpTokenExpiryHours;
	
	@Autowired
	private CommonBeans commonBeans;

	public AuthTokenDO authenticate(LoginDO loginDO) throws SchoolException, UserDisabledException {
		logger.info("start authenticate");
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDO.getEmail(), loginDO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = userService.getLoggedInUser();
		if (user == null) {
			throw new UserNotFoundException("User not found");
		}
		if (!user.isEnabled()) {
			throw new UserDisabledException("User is disabled");
		}
		ModelMapper mapper = new ModelMapper();
		TypeMap<User, UserDOmini> propertyMapper = mapper.createTypeMap(User.class, UserDOmini.class);

		Converter<Set<Role>, String> rolesConverter = src -> {
			StringJoiner sj = new StringJoiner(", ");
			for (Role role : src.getSource()) {
				sj.add(role.getAuthority());
			}
			return sj.toString();
		};

		propertyMapper.addMappings(map -> map.using(rolesConverter).map(User::getRoles, UserDOmini::setRoles));
		String jwtToken = jwtService.createToken(userService.getUserDetailsFromUser(user));
		AuthTokenDO authTokenDO = new AuthTokenDO(jwtToken, mapper.map(user, UserDOmini.class));
		tokenRepository.save(new Token(user.getEmail(), jwtToken, new Timestamp(System.currentTimeMillis()),
				new Timestamp(System.currentTimeMillis() + jwtExpirationHours * 60 * 60 * 1000), true));
		logger.info("end authenticate");
		return authTokenDO;
	}

	@Transactional
	public void logout(String authorization) {
		logger.info("start logout");
		Optional<Token> optionalToken = tokenRepository.findByToken(authorization.substring(7));
		if (optionalToken.isPresent()) {
			Token token = optionalToken.get();
			token.setValid(false);
			tokenRepository.save(token);
		}
		logger.info("end logout");
	}

	public NewTokenDO createSignUpToken(String email, String type) {
		logger.info("start createSignUpToken");
		ModelMapper mapper = new ModelMapper();
		NewUserToken newUserToken;
		if(!type.equals(Constants.ROLE_ADMIN)&&!type.equals(Constants.ROLE_LIBRARIAN)&&!type.equals(Constants.ROLE_USER)) {
			throw new InvalidInputsExceptions("Invalid Type. Please provide correct account type");
		}
		if (userService.getLoggedInUser().isAdmin()) {
			newUserToken = new NewUserToken(RandomGenerator.getRandomString(Constants.NEW_USER_TOKEN_LENGTH), email,
					type, new Timestamp(System.currentTimeMillis()+signUpTokenExpiryHours*60*60*1000), new Timestamp(System.currentTimeMillis()),
					userService.getLoggedInUser());
			newUserTokenRepository.save(newUserToken);
		} else if (type.equals(Constants.ROLE_USER)
				&& userService.getLoggedInUser().getRoles().contains(new Role("librarian", Constants.ROLE_LIBRARIAN))) {
			newUserToken = new NewUserToken(RandomGenerator.getRandomString(Constants.NEW_USER_TOKEN_LENGTH), email,
					type, new Timestamp(System.currentTimeMillis()+signUpTokenExpiryHours*60*60*1000), new Timestamp(System.currentTimeMillis()),
					userService.getLoggedInUser());
			newUserTokenRepository.save(newUserToken);
		} else {
			throw new UnauthorizedUserException("You do not have enougth access rigths to take this action");
		}
		logger.info("end createSignUpToken");
		return mapper.map(newUserToken, NewTokenDO.class);

	}

	public UserDO createAccount(@Valid CreateAccountDO createAccountDO) {
		logger.info("start createAccount");
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(createAccountDO, User.class);
		Optional<NewUserToken> optionalNewUserToken = newUserTokenRepository
				.findByTokenAndEmailIgnoreCase(createAccountDO.getToken(), createAccountDO.getEmail());
		if (!optionalNewUserToken.isPresent()) {
			throw new InvalidTokenException("Invalid token or email");
		}
		if(optionalNewUserToken.get().isExprired()) {
			newUserTokenRepository.delete(optionalNewUserToken.get());
			throw new InvalidTokenException("Token Expired");
		}
		String accountType=optionalNewUserToken.get().getAccountType();
		Role role=accountType.equals(Constants.ROLE_ADMIN)?commonBeans.getAdminRole():
			accountType.equals(Constants.ROLE_LIBRARIAN)?commonBeans.getLibrarianRole():
				commonBeans.getUserRole();
		user.setRoles(Set.of(role));
		user.setEnabled(true);
		addressRepository.save(user.getPermanentAddress());
		addressRepository.save(user.getTemporaryAddress());
		userRepository.save(user);
		
		mapper = new ModelMapper();
		TypeMap<User, UserDO> propertyMapper = mapper.createTypeMap(User.class, UserDO.class);

		Converter<Set<Role>, String> rolesConverter = src -> {
			StringJoiner sj = new StringJoiner(", ");
			for (Role role1 : src.getSource()) {
				sj.add(role1.getAuthority());
			}
			return sj.toString();
		};
		propertyMapper.addMappings(map -> map.using(rolesConverter).map(User::getRoles, UserDO::setRoles));
		UserDO userDO=mapper.map(user,UserDO.class);
		logger.info("end createAccount");
		return userDO;
	}
}
