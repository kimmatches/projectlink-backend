package projectlink.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projectlink.models.Label;
import projectlink.services.LabelService;

@RestController
@RequestMapping("/api/v1/cards/labels")
@Tag(name = "LabelController", description = "LabelController")
public class LabelController {

    private final LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    //라벨 생성
    @PostMapping(path = "/new")
    public Label createLabel(@RequestBody Label body) {
        return labelService.createLabel(body);
    }
    //라벨 업데이트
    @PutMapping
    public Label updateLabel(@RequestBody Label body) throws Exception {
        return labelService.updateLabel(body);
    }
}
