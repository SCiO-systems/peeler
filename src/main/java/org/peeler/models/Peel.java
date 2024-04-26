
package org.peeler.models;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "metadata_id",
    "text",
    "part",
    "chapter",
    "word_count",
    "language",
    "model"
})
@Generated("jsonschema2pojo")
public class Peel {

    @JsonProperty("metadata_id")
    private String metadataId;
    @JsonProperty("text")
    private String text;
    @JsonProperty("part")
    private Integer part;
    @JsonProperty("chapter")
    private String chapter;
    @JsonProperty("word_count")
    private Integer wordCount;
    @JsonProperty("language")
    private String language;
    @JsonProperty("model")
    private String model;


    public Peel(String metadataId, String text, Integer part, String chapter, Integer wordCount, String language, String model) {
        this.metadataId = metadataId;
        this.text = text;
        this.part = part;
        this.chapter = chapter;
        this.wordCount = wordCount;
        this.language = language;
        this.model = model;
    }

    @JsonProperty("metadata_id")
    public String getMetadataId() {
        return metadataId;
    }

    @JsonProperty("metadata_id")
    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("part")
    public Integer getPart() {
        return part;
    }

    @JsonProperty("part")
    public void setPart(Integer part) {
        this.part = part;
    }

    @JsonProperty("chapter")
    public String getChapter() {
        return chapter;
    }

    @JsonProperty("chapter")
    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    @JsonProperty("word_count")
    public Integer getWordCount() {
        return wordCount;
    }

    @JsonProperty("word_count")
    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("model")
    public String getModel() {
        return model;
    }

    @JsonProperty("model")
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Peel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("metadataId");
        sb.append('=');
        sb.append(((this.metadataId == null)?"<null>":this.metadataId));
        sb.append(',');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("part");
        sb.append('=');
        sb.append(((this.part == null)?"<null>":this.part));
        sb.append(',');
        sb.append("chapter");
        sb.append('=');
        sb.append(((this.chapter == null)?"<null>":this.chapter));
        sb.append(',');
        sb.append("wordCount");
        sb.append('=');
        sb.append(((this.wordCount == null)?"<null>":this.wordCount));
        sb.append(',');
        sb.append("language");
        sb.append('=');
        sb.append(((this.language == null)?"<null>":this.language));
        sb.append(',');
        sb.append("model");
        sb.append('=');
        sb.append(((this.model == null)?"<null>":this.model));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.metadataId == null)? 0 :this.metadataId.hashCode()));
        result = ((result* 31)+((this.chapter == null)? 0 :this.chapter.hashCode()));
        result = ((result* 31)+((this.wordCount == null)? 0 :this.wordCount.hashCode()));
        result = ((result* 31)+((this.part == null)? 0 :this.part.hashCode()));
        result = ((result* 31)+((this.language == null)? 0 :this.language.hashCode()));
        result = ((result* 31)+((this.model == null)? 0 :this.model.hashCode()));
        result = ((result* 31)+((this.text == null)? 0 :this.text.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Peel) == false) {
            return false;
        }
        Peel rhs = ((Peel) other);
        return ((((((((this.metadataId == rhs.metadataId)||((this.metadataId!= null)&&this.metadataId.equals(rhs.metadataId)))&&((this.chapter == rhs.chapter)||((this.chapter!= null)&&this.chapter.equals(rhs.chapter))))&&((this.wordCount == rhs.wordCount)||((this.wordCount!= null)&&this.wordCount.equals(rhs.wordCount))))&&((this.part == rhs.part)||((this.part!= null)&&this.part.equals(rhs.part))))&&((this.language == rhs.language)||((this.language!= null)&&this.language.equals(rhs.language))))&&((this.model == rhs.model)||((this.model!= null)&&this.model.equals(rhs.model))))&&((this.text == rhs.text)||((this.text!= null)&&this.text.equals(rhs.text))));
    }

}
