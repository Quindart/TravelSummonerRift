package vn.edu.iuh.fit.userservice.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.userservice.dtos.requests.UserRegisterRequest;
import vn.edu.iuh.fit.userservice.dtos.requests.UserUpdateRequest;
import vn.edu.iuh.fit.userservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.userservice.entities.User;
import vn.edu.iuh.fit.userservice.exception.errors.InternalServerErrorException;
import vn.edu.iuh.fit.userservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.userservice.mapper.UserMapper;
import vn.edu.iuh.fit.userservice.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CloudinaryService cloudinaryService;


    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        UserResponse userResponse = new UserResponse();
        userResponse = userMapper.toUserResponse(userRepository.findAll().get(0));
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse registerUser(UserRegisterRequest request) {
        String username = request.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new InternalServerErrorException("Tên đăng nhập đã tồn tại");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }


    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này"));

        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này."));
        return userMapper.toUserResponse(user);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này!"));

        user.setPhone(request.getPhone());
        user.setFullName(request.getFullName());
        user.setGender(request.getGender());
        user.setBirthday(request.getBirthday());

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }


    public UserResponse updateUserAvatar(String userId, MultipartFile avatar) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này!"));

        String avatarUrl = cloudinaryService.uploadImage(avatar);
        user.setAvatarUrl(avatarUrl);

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này!"));

        user.setActive(false);
        userRepository.save(user);
    }


}
