package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Interest;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.UserFirstSaveRequestDto;
import com.roomoftruth.rot.repository.InterestRepository;
import com.roomoftruth.rot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final InterestRepository interestRepository;

	public User findByNum(long num) {

		User user = userRepository.findByNum(num);

		return user;
	}

	public Long save(UserFirstSaveRequestDto requestDto) {

		User newUser = User.builder().num(requestDto.getNum()).build();

		userRepository.save(newUser);

		User user = findByNum(requestDto.getNum());

		Interest interest = Interest.builder().user(user).sd(requestDto.getSd()).sgg(requestDto.getSgg())
				.first(requestDto.getFirst()).second(requestDto.getSecond()).third(requestDto.getThird())
				.gender(requestDto.getGender()).birth(requestDto.getBirth()).build();

		return interestRepository.save(interest).getUser().getNum();
	}


//	@Autowired
//	private IUserDao userDao;
//
//	@Override
//	public List<User> getAllUser() {
//		return userDao.getAllUser();
//	}
//
//	@Override
//	public User getUserByNum(long num) {
//		return userDao.getUserByNum(num);
//	}
//
//	@Override
//	public int createUser(User user) {
//		return userDao.createUser(user);
//	}
//
//	@Override
//	public int updateEntered(User user) {
//		return userDao.updateEntered(user);
//	}
//
//	@Override
//	public int obtainAuth(long num) {
//		return userDao.obtainAuth(num);
//	}
//
//	@Override
//	public int updateUser(User user) {
//		return userDao.updateUser(user);
//	}
//
//	@Override
//	public int loseAuth(long num) {
//		return userDao.loseAuth(num);
//	}
}
