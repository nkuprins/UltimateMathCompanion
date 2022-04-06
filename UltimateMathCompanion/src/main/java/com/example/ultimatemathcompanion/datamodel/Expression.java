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
import java.time.LocalDateTime;

@Entity
@Table(name = "expressions")
@Getter
@NoArgsConstructor
public class Expression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expression_id")
    private int id;

    @Column(name = "expression")
    private String expression;

    @Column(name = "answer")
    private BigDecimal answer;

    @Column(name = "date")
    private String date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "type")
    private Types types;

    public Expression(String expression, BigDecimal answer, String date, Types types) {
        this.expression = expression;
        this.answer = answer;
        this.date = date;
        this.types = types;
    }

    public Expression(int id, String expression, BigDecimal answer, String date, Types types) {
        this.id = id;
        this.expression = expression;
        this.answer = answer;
        this.date = date;
        this.types = types;
    }
}
