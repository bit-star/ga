<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.groupMembers.home.createOrEditLabel"
          data-cy="GroupMembersCreateUpdateHeading"
          v-text="$t('gaApp.groupMembers.home.createOrEditLabel')"
        >
          Create or edit a GroupMembers
        </h2>
        <div>
          <div class="form-group" v-if="groupMembers.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="groupMembers.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.groupMembers.groupRole')" for="group-members-groupRole">Group Role</label>
            <select
              class="form-control"
              name="groupRole"
              :class="{ valid: !$v.groupMembers.groupRole.$invalid, invalid: $v.groupMembers.groupRole.$invalid }"
              v-model="$v.groupMembers.groupRole.$model"
              id="group-members-groupRole"
              data-cy="groupRole"
            >
              <option value="Ang" v-bind:label="$t('gaApp.GroupRole.Ang')">Ang</option>
              <option value="Refuse" v-bind:label="$t('gaApp.GroupRole.Refuse')">Refuse</option>
              <option value="Comment" v-bind:label="$t('gaApp.GroupRole.Comment')">Comment</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.groupMembers.conversation')" for="group-members-conversation"
              >Conversation</label
            >
            <select
              class="form-control"
              id="group-members-conversation"
              data-cy="conversation"
              name="conversation"
              v-model="groupMembers.conversation"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  groupMembers.conversation && conversationOption.id === groupMembers.conversation.id
                    ? groupMembers.conversation
                    : conversationOption
                "
                v-for="conversationOption in conversations"
                :key="conversationOption.id"
              >
                {{ conversationOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.groupMembers.ddUser')" for="group-members-ddUser">Dd User</label>
            <select class="form-control" id="group-members-ddUser" data-cy="ddUser" name="ddUser" v-model="groupMembers.ddUser">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="groupMembers.ddUser && ddUserOption.id === groupMembers.ddUser.id ? groupMembers.ddUser : ddUserOption"
                v-for="ddUserOption in ddUsers"
                :key="ddUserOption.id"
              >
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
            :disabled="$v.groupMembers.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./group-members-update.component.ts"></script>
