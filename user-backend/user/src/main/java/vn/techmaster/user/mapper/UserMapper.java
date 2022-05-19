package vn.techmaster.user.mapper;

import vn.techmaster.user.dto.UserDto;
import vn.techmaster.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setAddress(user.getAddress());
        userDto.setId(user.getId());
        userDto.setAvatar(user.getAvatar());
        return userDto;
    }

}
