package com.spomprt.weightminder.domain;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("urls")
public class Url implements Serializable {

    private Long id;
    private String url;

}
