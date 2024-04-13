package codesquad.springcafe.user;

import codesquad.springcafe.user.DTO.Alert;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import codesquad.springcafe.user.DTO.UserListRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // action
    @PostMapping("/login")
    public String login(@RequestParam("userId") String id,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        Model model) {

        SimpleUserInfo userInfo;
        if ((userInfo = userService.login(id, password)) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", userInfo);
            session.setMaxInactiveInterval(60 * 30);

            log.info("로그인됨 : " + id);
        }
        else {
            addAlert(model).add(new Alert("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요."));
            return "user/login";
        }

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @PostMapping("")
    public String createUser(@ModelAttribute User user, Model model) {
        try {
            userService.create(user);
        } catch (IllegalArgumentException alreadyExistsId) {
            addAlert(model).add(new Alert("이미 존재하는 ID 입니다"));
            return "user/form";
        }

        log.debug(user.toString());
        return "redirect:/user/users";
    }

    @PostMapping("/update")
    public String update(@RequestParam("prev_password") String prevPassword,
                         @ModelAttribute("user") User user,
                         Model model) {

        List<Alert> alerts = addAlert(model);
        if (userService.update(user, prevPassword)) {
            alerts.add(new Alert("회원 정보 변경 성공!"));
        } else {
            alerts.add(new Alert("비밀번호가 일치하지 않습니다!"));
        }

        model.addAttribute("userId", user.getUserId());
        return "user/update_form";
    }


    // view
    @GetMapping("/users")
    public String userList(Model model) {
        List<UserListRes> users = userService.userList();

        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") String id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user/profile";
    }


    // form
    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("/form")
    public String registerForm() {
        return "user/form";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("user", userService.getUser(id));

        return "user/update_form";
    }


    //
    private List<Alert> addAlert(Model model) {
        List<Alert> alerts = new ArrayList<>();
        model.addAttribute("alerts", alerts);

        return alerts;
    }
}
