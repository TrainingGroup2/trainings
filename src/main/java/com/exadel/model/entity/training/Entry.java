package com.exadel.model.entity.training;

import com.exadel.dto.EntryDTO;
import com.exadel.model.entity.user.Absentee;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Index;
import java.util.Date;
import java.util.List;

@Entity
@Indexed
@Table(name = "entries")
@AnalyzerDef(name = "canalyzer",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @Parameter(name = "language", value = "English")
                }),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @Parameter(name = "language", value = "Russian")
                })

        })
public class Entry implements Comparable<Entry> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String place;

    @Field( analyze = Analyze.YES, store = Store.YES)
    @Analyzer(definition = "canalyzer")
    @Column(name = "begin_time")
    private Date beginTime;
    @Column(name = "end_time")
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
    private List<Absentee> absentees;

    public Entry() {
    }

    public Entry(EntryDTO entryDTO) {
        this.id = entryDTO.getId();
        this.place = entryDTO.getPlace();
        this.beginTime = entryDTO.getBeginTime();
        this.endTime = entryDTO.getEndTime();
    }

    public void updateEntry(EntryDTO entryDTO) {
        this.place = entryDTO.getPlace();
        this.beginTime = entryDTO.getBeginTime();
        this.endTime = entryDTO.getEndTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public List<Absentee> getAbsentees() {
        return absentees;
    }

    public void setAbsentees(List<Absentee> absentees) {
        this.absentees = absentees;
    }

    @Override
    public int compareTo(Entry entry) {
        return (int) (this.getBeginTime().getTime() - entry.getBeginTime().getTime());
    }
}
