package com.efecavusoglu.haratrescaseproject.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class UserRequest {
    private String userName;
    private String userPassword;

}
