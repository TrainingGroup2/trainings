package com.exadel.model.entity.user;

import com.exadel.dto.NewTrainerDTO;
import com.exadel.dto.UserDTO;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.LengthFilterFactory;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceFilter;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.util.PagedBytes;
import org.apache.lucene.util.packed.PackedInts;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.bridge.builtin.EnumBridge;

import javax.persistence.*;

import org.hibernate.search.annotations.Index;

@Entity
@Indexed
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.INTEGER)
@AnalyzerDef(name = "customanalyz",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @Parameter(name = "language", value = "English")
                }),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @Parameter(name = "language", value = "Russian")
                }),
                @TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
                        @Parameter(name = "minGramSize", value = "1"), @Parameter(name = "maxGramSize", value = "15")}),

        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Field(analyze = Analyze.YES, store = Store.NO)
    @Analyzer(definition = "customanalyz")
    @Column(name = "name", nullable = false)
    private String name;


    @Field(analyze = Analyze.YES, store = Store.NO)
    @Analyzer(definition = "customanalyz")
    @Column(name = "surname", nullable = false)
    private String surname;



    @Field(analyze = Analyze.YES, store = Store.NO)
    @Analyzer(definition = "customanalyz")
    @Column(name = "phone", nullable = false)
    private String phone;


    @Field(analyze = Analyze.YES, store = Store.NO)
    @Analyzer(definition = "customanalyz")
    private String email;

    @Field(analyze = Analyze.YES, index = Index.YES)
    @Column(insertable = false, updatable = false)
    @FieldBridge(impl = EnumBridge.class)
    private UserRole role;

    public User() {
    }

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.role = userDTO.getRole();
    }

    public User(NewTrainerDTO newTrainerDTO) {
        this.id = newTrainerDTO.getId();
        this.name = newTrainerDTO.getName();
        this.surname = newTrainerDTO.getSurname();
        this.phone = newTrainerDTO.getPhone();
        this.email = newTrainerDTO.getEmail();
        this.role = newTrainerDTO.getRole();
    }

    public void update(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
    }

    public void update(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.phone = user.getPhone();
        this.email = user.getEmail();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}
