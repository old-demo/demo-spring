package com.heqing.demo.spring.convert;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Address {

    private String provice;
    private String city;
    private String area;

}
