package org.lwl.test.exam;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class ScoreResultResp implements Serializable {
    private List<ScoreResultDto> data;
    private String status;
    private Integer total;
}
