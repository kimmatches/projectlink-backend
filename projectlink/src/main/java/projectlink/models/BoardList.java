package projectlink.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "lists")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoardList {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUIDv4")
    @GenericGenerator(name = "UUIDv4", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonIgnoreProperties(value = {"lists", "cards"}, allowSetters = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Board board;

    @OrderColumn
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE })
    @JsonIgnoreProperties(value = "boardList", allowSetters = true, allowGetters = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private List<Card> cards;

    @Column
    private boolean archived;

}