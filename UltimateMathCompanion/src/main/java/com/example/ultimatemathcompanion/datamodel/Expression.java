/*
 * Copyright 2021 Nikita Kuprins
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ultimatemathcompanion.datamodel;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "expressions")
@Getter
@Setter
@NoArgsConstructor
public class Expression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expression_id")
    private int id;

    // Valid format is: 2 / 3 + 1 * 4
    // No redundant spaces or unclear symbols.
    // All numbers are only integers.
    @Column(name = "expression")
    @Pattern(regexp = "^-?\\d+( [+\\-*/] -?\\d+)+$")
    private String expression;

    @Column(name = "answer")
    private BigDecimal answer;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "type")
    private Types types;

    public Expression(String expression, BigDecimal answer, Date date, Types types) {
        this.expression = expression;
        this.answer = answer;
        this.date = date;
        this.types = types;
    }
}
