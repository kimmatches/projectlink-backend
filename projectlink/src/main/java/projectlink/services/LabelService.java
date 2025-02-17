package projectlink.services;

import org.springframework.stereotype.Service;
import projectlink.models.Label;
import projectlink.repositories.LabelRepository;

@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label createLabel(Label label) {
        return labelRepository.save(label);
    }

    public Label updateLabel(Label label) throws Exception {
        boolean exists = labelRepository.existsById(label.getId());
        if (!exists) {
            throw new Exception("Label does not exist");
        }
        return labelRepository.save(label);

    }
}