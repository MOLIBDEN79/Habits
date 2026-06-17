package com.habits.habits.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "habit", schema = "habits_corp")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String trigger;

    @Column
    private String outcome;

    @Column
    private String routine;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "category_id")
    @Cascade(CascadeType.ALL) // или CascadeType.PERSIST, в зависимости от вашей логики
    private Category category;


    @OneToMany(mappedBy = "habit", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PracticeTracker> practiceTrackerList;

    public Habit() {
    }

    public Habit(Long id, String name, String trigger, String outcome, String routine, User user, Category category) {
        this.id = id;
        this.name = name;
        this.trigger = trigger;
        this.outcome = outcome;
        this.routine = routine;
        this.user = user;
        this.category = category;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    public List<PracticeTracker> getPracticeTrackerList() {
        return practiceTrackerList;
    }

    public void setPracticeTrackerList(List<PracticeTracker> practiceTrackerList) {
        this.practiceTrackerList = practiceTrackerList;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trigger='" + trigger + '\'' +
                ", outcome='" + outcome + '\'' +
                ", routine='" + routine + '\'' +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}
