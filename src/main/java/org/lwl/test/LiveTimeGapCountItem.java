package org.lwl.test;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LiveTimeGapCountItem implements Serializable {
    private Date date;

    private Long count = 0L;

    private Long enterCount = 0L;

    private Long exitCount = 0L;
}
