<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.conversation.home.createOrEditLabel"
          data-cy="ConversationCreateUpdateHeading"
          v-text="$t('gaApp.conversation.home.createOrEditLabel')"
        >
          Create or edit a Conversation
        </h2>
        <div>
          <div class="form-group" v-if="conversation.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="conversation.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.name')" for="conversation-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="conversation-name"
              data-cy="name"
              :class="{ valid: !$v.conversation.name.$invalid, invalid: $v.conversation.name.$invalid }"
              v-model="$v.conversation.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.title')" for="conversation-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="conversation-title"
              data-cy="title"
              :class="{ valid: !$v.conversation.title.$invalid, invalid: $v.conversation.title.$invalid }"
              v-model="$v.conversation.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.owner')" for="conversation-owner">Owner</label>
            <input
              type="text"
              class="form-control"
              name="owner"
              id="conversation-owner"
              data-cy="owner"
              :class="{ valid: !$v.conversation.owner.$invalid, invalid: $v.conversation.owner.$invalid }"
              v-model="$v.conversation.owner.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.ownerUserId')" for="conversation-ownerUserId"
              >Owner User Id</label
            >
            <input
              type="text"
              class="form-control"
              name="ownerUserId"
              id="conversation-ownerUserId"
              data-cy="ownerUserId"
              :class="{ valid: !$v.conversation.ownerUserId.$invalid, invalid: $v.conversation.ownerUserId.$invalid }"
              v-model="$v.conversation.ownerUserId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.chatid')" for="conversation-chatid">Chatid</label>
            <input
              type="text"
              class="form-control"
              name="chatid"
              id="conversation-chatid"
              data-cy="chatid"
              :class="{ valid: !$v.conversation.chatid.$invalid, invalid: $v.conversation.chatid.$invalid }"
              v-model="$v.conversation.chatid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.openConversationId')" for="conversation-openConversationId"
              >Open Conversation Id</label
            >
            <input
              type="text"
              class="form-control"
              name="openConversationId"
              id="conversation-openConversationId"
              data-cy="openConversationId"
              :class="{ valid: !$v.conversation.openConversationId.$invalid, invalid: $v.conversation.openConversationId.$invalid }"
              v-model="$v.conversation.openConversationId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.conversationTag')" for="conversation-conversationTag"
              >Conversation Tag</label
            >
            <input
              type="number"
              class="form-control"
              name="conversationTag"
              id="conversation-conversationTag"
              data-cy="conversationTag"
              :class="{ valid: !$v.conversation.conversationTag.$invalid, invalid: $v.conversation.conversationTag.$invalid }"
              v-model.number="$v.conversation.conversationTag.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.useridlist')" for="conversation-useridlist">Useridlist</label>
            <textarea
              class="form-control"
              name="useridlist"
              id="conversation-useridlist"
              data-cy="useridlist"
              :class="{ valid: !$v.conversation.useridlist.$invalid, invalid: $v.conversation.useridlist.$invalid }"
              v-model="$v.conversation.useridlist.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.uuid')" for="conversation-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="conversation-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.conversation.uuid.$invalid, invalid: $v.conversation.uuid.$invalid }"
              v-model="$v.conversation.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.icon')" for="conversation-icon">Icon</label>
            <input
              type="text"
              class="form-control"
              name="icon"
              id="conversation-icon"
              data-cy="icon"
              :class="{ valid: !$v.conversation.icon.$invalid, invalid: $v.conversation.icon.$invalid }"
              v-model="$v.conversation.icon.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.showHistoryType')" for="conversation-showHistoryType"
              >Show History Type</label
            >
            <input
              type="text"
              class="form-control"
              name="showHistoryType"
              id="conversation-showHistoryType"
              data-cy="showHistoryType"
              :class="{ valid: !$v.conversation.showHistoryType.$invalid, invalid: $v.conversation.showHistoryType.$invalid }"
              v-model="$v.conversation.showHistoryType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.searchable')" for="conversation-searchable">Searchable</label>
            <input
              type="text"
              class="form-control"
              name="searchable"
              id="conversation-searchable"
              data-cy="searchable"
              :class="{ valid: !$v.conversation.searchable.$invalid, invalid: $v.conversation.searchable.$invalid }"
              v-model="$v.conversation.searchable.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.validationType')" for="conversation-validationType"
              >Validation Type</label
            >
            <input
              type="text"
              class="form-control"
              name="validationType"
              id="conversation-validationType"
              data-cy="validationType"
              :class="{ valid: !$v.conversation.validationType.$invalid, invalid: $v.conversation.validationType.$invalid }"
              v-model="$v.conversation.validationType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.chatBannedType')" for="conversation-chatBannedType"
              >Chat Banned Type</label
            >
            <input
              type="text"
              class="form-control"
              name="chatBannedType"
              id="conversation-chatBannedType"
              data-cy="chatBannedType"
              :class="{ valid: !$v.conversation.chatBannedType.$invalid, invalid: $v.conversation.chatBannedType.$invalid }"
              v-model="$v.conversation.chatBannedType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.mentionAllAuthority')" for="conversation-mentionAllAuthority"
              >Mention All Authority</label
            >
            <input
              type="text"
              class="form-control"
              name="mentionAllAuthority"
              id="conversation-mentionAllAuthority"
              data-cy="mentionAllAuthority"
              :class="{ valid: !$v.conversation.mentionAllAuthority.$invalid, invalid: $v.conversation.mentionAllAuthority.$invalid }"
              v-model="$v.conversation.mentionAllAuthority.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.managementType')" for="conversation-managementType"
              >Management Type</label
            >
            <input
              type="text"
              class="form-control"
              name="managementType"
              id="conversation-managementType"
              data-cy="managementType"
              :class="{ valid: !$v.conversation.managementType.$invalid, invalid: $v.conversation.managementType.$invalid }"
              v-model="$v.conversation.managementType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.templateId')" for="conversation-templateId">Template Id</label>
            <input
              type="text"
              class="form-control"
              name="templateId"
              id="conversation-templateId"
              data-cy="templateId"
              :class="{ valid: !$v.conversation.templateId.$invalid, invalid: $v.conversation.templateId.$invalid }"
              v-model="$v.conversation.templateId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.officialGroup')" for="conversation-officialGroup"
              >Official Group</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="officialGroup"
              id="conversation-officialGroup"
              data-cy="officialGroup"
              :class="{ valid: !$v.conversation.officialGroup.$invalid, invalid: $v.conversation.officialGroup.$invalid }"
              v-model="$v.conversation.officialGroup.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.enableScenegroup')" for="conversation-enableScenegroup"
              >Enable Scenegroup</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="enableScenegroup"
              id="conversation-enableScenegroup"
              data-cy="enableScenegroup"
              :class="{ valid: !$v.conversation.enableScenegroup.$invalid, invalid: $v.conversation.enableScenegroup.$invalid }"
              v-model="$v.conversation.enableScenegroup.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.groupUrl')" for="conversation-groupUrl">Group Url</label>
            <input
              type="text"
              class="form-control"
              name="groupUrl"
              id="conversation-groupUrl"
              data-cy="groupUrl"
              :class="{ valid: !$v.conversation.groupUrl.$invalid, invalid: $v.conversation.groupUrl.$invalid }"
              v-model="$v.conversation.groupUrl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.conversation.time')" for="conversation-time">Time</label>
            <div class="d-flex">
              <input
                id="conversation-time"
                data-cy="time"
                type="datetime-local"
                class="form-control"
                name="time"
                :class="{ valid: !$v.conversation.time.$invalid, invalid: $v.conversation.time.$invalid }"
                :value="convertDateTimeFromServer($v.conversation.time.$model)"
                @change="updateInstantField('time', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label v-text="$t('gaApp.conversation.ddUser')" for="conversation-ddUser">Dd User</label>
            <select
              class="form-control"
              id="conversation-ddUser"
              data-cy="ddUser"
              multiple
              name="ddUser"
              v-if="conversation.ddUsers !== undefined"
              v-model="conversation.ddUsers"
            >
              <option v-bind:value="getSelected(conversation.ddUsers, ddUserOption)" v-for="ddUserOption in ddUsers" :key="ddUserOption.id">
                {{ ddUserOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.conversation.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./conversation-update.component.ts"></script>
