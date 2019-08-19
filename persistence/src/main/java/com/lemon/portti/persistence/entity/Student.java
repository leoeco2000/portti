package com.lemon.portti.persistence.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class Student implements Serializable {
  private int id;
  private String name;
  private int sex;    // 0=male, 1=female
  private String addr;

}