package com.example.demo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orderall implements Comparable<Orderall>{
    int a;
    int id;

    @Override
    public int compareTo(Orderall o) {
        return o.a-this.a;
    }
}
