package study.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private Long id = 1L;
    private List<User> userList = new ArrayList<>();

    @PostMapping("/user")
    @Operation(summary = "add user", description = "회원 등록 시 사용되는 api")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "4xx", description = "실패")
    })
    @Parameters({
            @Parameter(name = "name", description = "이름", example = "홍길동"),
            @Parameter(name = "password", description = "비밀번호", example = "password1234!")
    })
    public String addUser(@RequestBody UserAddDto userAddDto) {
        userList.add(new User(id, userAddDto.getName(), userAddDto.getPassword()));
        return id++ + "번 회원 등록 완료";
    }

    @GetMapping("/user/{id}")
    public UserDto findUser(@PathVariable int id) {
        User findUser = userList.get(id-1);
        return new UserDto(findUser.getId(), findUser.getName());
    }

    @GetMapping("/user/list")
    public All findAllUsers() {
        List<UserDto> userDtos = userList.stream()
                .map(m -> new UserDto(m.getId(), m.getName()))
                .collect(Collectors.toList());

        return new All(userDtos.size(), userDtos);
    }



    @Data
    @AllArgsConstructor
    static class All<T> {
        private int count;
        private T userData;
    }

    @Data
    @AllArgsConstructor
    static class UserDto {
        private Long id;
        private String name;
    }

}

