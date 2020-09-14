package com.heqing.demo.spring.convert;

import com.alibaba.fastjson.JSONObject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Test;

import java.util.*;

public class OrikaTest {

    private final static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    private final static MapperFacade mapperFacade = mapperFactory.getMapperFacade();

    @Test
    public void testBase() {
        JSONObject remark = new JSONObject();
        remark.put("number", 1);
        remark.put("string", "test");

        Address address = new Address();
        address.setProvice("安徽");
        address.setCity("安庆");
        address.setArea("宿松");

        PersonBO personBO = new PersonBO();
        personBO.setId(1L);
        personBO.setName("贺小白");
        personBO.setSex(1);
        personBO.setDate(new Date());
        List<String> nameParts = new ArrayList<>();
        nameParts.add("贺");
        nameParts.add("小白");
        personBO.setNameParts(nameParts);
        Map<String, Object> map = new HashMap<>();
        map.put("number", 1);
        map.put("string", "test");
        personBO.setRemarkMap(map);
        personBO.setAddress(address);
        personBO.setType(PersonEnum.ADULT);
        personBO.setRemarkJson(remark.toJSONString());

        System.out.println("BO --> "+personBO);
        mapperFactory.classMap(PersonBO.class, PersonDTO.class)
                .field("sex", "gender")
                .field("nameParts[0]", "firstName")
                .field("nameParts[1]", "lastName")
                .byDefault().register();
        PersonDTO personDTO = new PersonDTO();
        mapperFacade.map(personBO, personDTO);
        System.out.println("DTO --> "+personDTO);
    }

    @Test
    public void testBaseList() {
        JSONObject remark = new JSONObject();
        remark.put("number", 1);
        remark.put("string", "test");

        List<PersonDTO> personDTOList = new ArrayList<>();
        for(int i=0; i<3; i++) {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setId(1L+i);
            personDTO.setName("贺小白");
            personDTO.setGender("1");
            personDTO.setRemarkJson(remark.toJSONString());
//            personDTO.setDate("2020-09-09 19:37:41");
            personDTO.setFirstName("贺");
            personDTO.setLastName("小白");
            Map<String, Object> map = new HashMap<>();
            map.put("number", 1+i);
            map.put("string", "test"+i);
            personDTO.setRemarkMap(map);
            personDTO.setType(PersonEnum.ADULT);
            personDTOList.add(personDTO);
        }

        System.out.println("DTO --> "+JSONObject.toJSONString(personDTOList));
        mapperFactory.classMap(PersonBO.class, PersonDTO.class)
                .field("sex", "gender")
                .field("nameParts[0]", "firstName")
                .field("nameParts[1]", "lastName")
                .byDefault().register();
        List<PersonBO> personBOList = mapperFacade.mapAsList(personDTOList, PersonBO.class);
        System.out.println("BO --> "+JSONObject.toJSONString(personBOList));
    }
}
