package codesquad.springcafe.domain.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    private final ArticleService articleService;

    @Autowired
    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("")
    public String index(Model model){
        model.addAttribute("articles", articleService.getArticlesAtPage(1));
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam Map<String, String> query, Model model){
        List<Article> articles = articleService.findWithQuery(query);
        model.addAttribute("articles", articles);
        return "index";
    }
}
