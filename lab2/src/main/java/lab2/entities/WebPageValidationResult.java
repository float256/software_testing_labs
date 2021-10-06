package lab2.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
public class WebPageValidationResult {
    private Map<String, String> validLinks;
    private Map<String, String> invalidLinks;
}
