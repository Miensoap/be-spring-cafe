package codesquad.springcafe.article;

import codesquad.springcafe.article.DTO.ArticlePostReq;
import codesquad.springcafe.article.DTO.ArticleWithComments;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    // action
    @PostMapping("")
    public String postArticle(@ModelAttribute ArticlePostReq articlePostReq, HttpServletRequest request){
        SimpleUserInfo author = (SimpleUserInfo) request.getSession().getAttribute("loginUser");
        articleService.postArticle(articlePostReq, author);

        return "redirect:/";
    }


    // view
    @GetMapping("/{id}")
    public String showArticle(@PathVariable("id") int id, Model model, HttpServletResponse response) {
        ArticleWithComments article = articleService.getArticle(id);

        // 존재하지 않는 게시글
        if(article == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        // 정상 흐름
        articleService.addPoint(article.article());
        model.addAttribute("article", article.article());
        model.addAttribute("comments", article.comments());
        model.addAttribute("numberOfComments", article.comments().size());
        return "article/show";
    }


    // form
    @GetMapping("/form")
    public String articleForm(){
        return "article/form";
    }
}
