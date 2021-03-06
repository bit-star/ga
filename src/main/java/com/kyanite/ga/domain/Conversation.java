package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Conversation.
 */
@Entity
@Table(name = "conversation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "owner")
    private String owner;

    @Column(name = "owner_user_id")
    private String ownerUserId;

    @Column(name = "chatid")
    private String chatid;

    @Column(name = "open_conversation_id")
    private String openConversationId;

    @Column(name = "conversation_tag")
    private Integer conversationTag;

    @Lob
    @Column(name = "useridlist")
    private String useridlist;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "icon")
    private String icon;

    @Column(name = "show_history_type")
    private String showHistoryType;

    @Column(name = "searchable")
    private String searchable;

    @Column(name = "validation_type")
    private String validationType;

    @Column(name = "chat_banned_type")
    private String chatBannedType;

    @Column(name = "mention_all_authority")
    private String mentionAllAuthority;

    @Column(name = "management_type")
    private String managementType;

    @Column(name = "template_id")
    private String templateId;

    @Column(name = "official_group")
    private Boolean officialGroup;

    @Column(name = "enable_scenegroup")
    private Boolean enableScenegroup;

    @Column(name = "group_url")
    private String groupUrl;

    @Column(name = "time")
    private Instant time;

    @OneToMany(mappedBy = "conversation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "topboxes", "privateCardData", "operationResults", "confirmCards", "alertCards", "workflowInstance", "conversation" },
        allowSetters = true
    )
    private Set<PublicCardData> publicCardData = new HashSet<>();

    @OneToMany(mappedBy = "conversation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "publicCardData", "conversation" }, allowSetters = true)
    private Set<Topboxes> topboxes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_conversation__dd_user",
        joinColumns = @JoinColumn(name = "conversation_id"),
        inverseJoinColumns = @JoinColumn(name = "dd_user_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "privateCardData", "approvers", "operationResults", "createdInstances", "conversations" },
        allowSetters = true
    )
    private Set<DdUser> ddUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Conversation id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Conversation name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public Conversation title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return this.owner;
    }

    public Conversation owner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerUserId() {
        return this.ownerUserId;
    }

    public Conversation ownerUserId(String ownerUserId) {
        this.setOwnerUserId(ownerUserId);
        return this;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getChatid() {
        return this.chatid;
    }

    public Conversation chatid(String chatid) {
        this.setChatid(chatid);
        return this;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getOpenConversationId() {
        return this.openConversationId;
    }

    public Conversation openConversationId(String openConversationId) {
        this.setOpenConversationId(openConversationId);
        return this;
    }

    public void setOpenConversationId(String openConversationId) {
        this.openConversationId = openConversationId;
    }

    public Integer getConversationTag() {
        return this.conversationTag;
    }

    public Conversation conversationTag(Integer conversationTag) {
        this.setConversationTag(conversationTag);
        return this;
    }

    public void setConversationTag(Integer conversationTag) {
        this.conversationTag = conversationTag;
    }

    public String getUseridlist() {
        return this.useridlist;
    }

    public Conversation useridlist(String useridlist) {
        this.setUseridlist(useridlist);
        return this;
    }

    public void setUseridlist(String useridlist) {
        this.useridlist = useridlist;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Conversation uuid(String uuid) {
        this.setUuid(uuid);
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIcon() {
        return this.icon;
    }

    public Conversation icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShowHistoryType() {
        return this.showHistoryType;
    }

    public Conversation showHistoryType(String showHistoryType) {
        this.setShowHistoryType(showHistoryType);
        return this;
    }

    public void setShowHistoryType(String showHistoryType) {
        this.showHistoryType = showHistoryType;
    }

    public String getSearchable() {
        return this.searchable;
    }

    public Conversation searchable(String searchable) {
        this.setSearchable(searchable);
        return this;
    }

    public void setSearchable(String searchable) {
        this.searchable = searchable;
    }

    public String getValidationType() {
        return this.validationType;
    }

    public Conversation validationType(String validationType) {
        this.setValidationType(validationType);
        return this;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    public String getChatBannedType() {
        return this.chatBannedType;
    }

    public Conversation chatBannedType(String chatBannedType) {
        this.setChatBannedType(chatBannedType);
        return this;
    }

    public void setChatBannedType(String chatBannedType) {
        this.chatBannedType = chatBannedType;
    }

    public String getMentionAllAuthority() {
        return this.mentionAllAuthority;
    }

    public Conversation mentionAllAuthority(String mentionAllAuthority) {
        this.setMentionAllAuthority(mentionAllAuthority);
        return this;
    }

    public void setMentionAllAuthority(String mentionAllAuthority) {
        this.mentionAllAuthority = mentionAllAuthority;
    }

    public String getManagementType() {
        return this.managementType;
    }

    public Conversation managementType(String managementType) {
        this.setManagementType(managementType);
        return this;
    }

    public void setManagementType(String managementType) {
        this.managementType = managementType;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public Conversation templateId(String templateId) {
        this.setTemplateId(templateId);
        return this;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Boolean getOfficialGroup() {
        return this.officialGroup;
    }

    public Conversation officialGroup(Boolean officialGroup) {
        this.setOfficialGroup(officialGroup);
        return this;
    }

    public void setOfficialGroup(Boolean officialGroup) {
        this.officialGroup = officialGroup;
    }

    public Boolean getEnableScenegroup() {
        return this.enableScenegroup;
    }

    public Conversation enableScenegroup(Boolean enableScenegroup) {
        this.setEnableScenegroup(enableScenegroup);
        return this;
    }

    public void setEnableScenegroup(Boolean enableScenegroup) {
        this.enableScenegroup = enableScenegroup;
    }

    public String getGroupUrl() {
        return this.groupUrl;
    }

    public Conversation groupUrl(String groupUrl) {
        this.setGroupUrl(groupUrl);
        return this;
    }

    public void setGroupUrl(String groupUrl) {
        this.groupUrl = groupUrl;
    }

    public Instant getTime() {
        return this.time;
    }

    public Conversation time(Instant time) {
        this.setTime(time);
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Set<PublicCardData> getPublicCardData() {
        return this.publicCardData;
    }

    public void setPublicCardData(Set<PublicCardData> publicCardData) {
        if (this.publicCardData != null) {
            this.publicCardData.forEach(i -> i.setConversation(null));
        }
        if (publicCardData != null) {
            publicCardData.forEach(i -> i.setConversation(this));
        }
        this.publicCardData = publicCardData;
    }

    public Conversation publicCardData(Set<PublicCardData> publicCardData) {
        this.setPublicCardData(publicCardData);
        return this;
    }

    public Conversation addPublicCardData(PublicCardData publicCardData) {
        this.publicCardData.add(publicCardData);
        publicCardData.setConversation(this);
        return this;
    }

    public Conversation removePublicCardData(PublicCardData publicCardData) {
        this.publicCardData.remove(publicCardData);
        publicCardData.setConversation(null);
        return this;
    }

    public Set<Topboxes> getTopboxes() {
        return this.topboxes;
    }

    public void setTopboxes(Set<Topboxes> topboxes) {
        if (this.topboxes != null) {
            this.topboxes.forEach(i -> i.setConversation(null));
        }
        if (topboxes != null) {
            topboxes.forEach(i -> i.setConversation(this));
        }
        this.topboxes = topboxes;
    }

    public Conversation topboxes(Set<Topboxes> topboxes) {
        this.setTopboxes(topboxes);
        return this;
    }

    public Conversation addTopboxes(Topboxes topboxes) {
        this.topboxes.add(topboxes);
        topboxes.setConversation(this);
        return this;
    }

    public Conversation removeTopboxes(Topboxes topboxes) {
        this.topboxes.remove(topboxes);
        topboxes.setConversation(null);
        return this;
    }

    public Set<DdUser> getDdUsers() {
        return this.ddUsers;
    }

    public void setDdUsers(Set<DdUser> ddUsers) {
        this.ddUsers = ddUsers;
    }

    public Conversation ddUsers(Set<DdUser> ddUsers) {
        this.setDdUsers(ddUsers);
        return this;
    }

    public Conversation addDdUser(DdUser ddUser) {
        this.ddUsers.add(ddUser);
        ddUser.getConversations().add(this);
        return this;
    }

    public Conversation removeDdUser(DdUser ddUser) {
        this.ddUsers.remove(ddUser);
        ddUser.getConversations().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conversation)) {
            return false;
        }
        return id != null && id.equals(((Conversation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Conversation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", title='" + getTitle() + "'" +
            ", owner='" + getOwner() + "'" +
            ", ownerUserId='" + getOwnerUserId() + "'" +
            ", chatid='" + getChatid() + "'" +
            ", openConversationId='" + getOpenConversationId() + "'" +
            ", conversationTag=" + getConversationTag() +
            ", useridlist='" + getUseridlist() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", icon='" + getIcon() + "'" +
            ", showHistoryType='" + getShowHistoryType() + "'" +
            ", searchable='" + getSearchable() + "'" +
            ", validationType='" + getValidationType() + "'" +
            ", chatBannedType='" + getChatBannedType() + "'" +
            ", mentionAllAuthority='" + getMentionAllAuthority() + "'" +
            ", managementType='" + getManagementType() + "'" +
            ", templateId='" + getTemplateId() + "'" +
            ", officialGroup='" + getOfficialGroup() + "'" +
            ", enableScenegroup='" + getEnableScenegroup() + "'" +
            ", groupUrl='" + getGroupUrl() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
