package com.roomoftruth.rot.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@Getter
@Entity
public class Around {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "around_id")
    private Long aroundId;

    private String address;
    private int trans;
    private int comforts;
    private int education;
    private int medical;
    private int eatery;
    private int culture;

    @OneToMany(mappedBy = "around")
    @JsonManagedReference
    private List<Favorite> favorites = new ArrayList<>();

    @Builder
    public Around(Long aroundId, int trans, int comforts, int education,
                  int medical, int eatery, int culture) {
        this.aroundId = aroundId;
        this.trans = trans;
        this.comforts = comforts;
        this.education = education;
        this.medical = medical;
        this.eatery = eatery;
        this.culture = culture;
    }
}
