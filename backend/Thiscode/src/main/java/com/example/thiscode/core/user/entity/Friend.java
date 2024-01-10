package com.example.thiscode.core.user.entity;

import com.example.thiscode.core.common.BaseEntity;
import com.example.thiscode.core.user.entity.type.State;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "friend")
@Entity
public class Friend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    @BatchSize(size = 100)
    private User sender;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    @BatchSize(size = 100)
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State friendState;

    public Friend(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.friendState = State.REQUEST;
    }

    public void updateState(State state) {
        this.friendState = state;
    }

}
