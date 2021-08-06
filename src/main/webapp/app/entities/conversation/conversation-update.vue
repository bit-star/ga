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
