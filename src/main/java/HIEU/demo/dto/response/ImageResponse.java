package HIEU.demo.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ImageResponse {
    private Long id;
    private String url;
}
