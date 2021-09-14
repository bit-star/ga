<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.topboxes.home.createOrEditLabel"
          data-cy="TopboxesCreateUpdateHeading"
          v-text="$t('gaApp.topboxes.home.createOrEditLabel')"
        >
          Create or edit a Topboxes
        </h2>
        <div>
          <div class="form-group" v-if="topboxes.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="topboxes.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.topboxes.text')" for="topboxes-text">Text</label>
            <input
              type="text"
              class="form-control"
              name="text"
              id="topboxes-text"
              data-cy="text"
              :class="{ valid: !$v.topboxes.text.$invalid, invalid: $v.topboxes.text.$invalid }"
              v-model="$v.topboxes.text.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.topboxes.link')" for="topboxes-link">Link</label>
            <input
              type="text"
              class="form-control"
              name="link"
              id="topboxes-link"
              data-cy="link"
              :class="{ valid: !$v.topboxes.link.$invalid, invalid: $v.topboxes.link.$invalid }"
              v-model="$v.topboxes.link.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.topboxes.cardId')" for="topboxes-cardId">Card Id</label>
            <input
              type="text"
              class="form-control"
              name="cardId"
              id="topboxes-cardId"
              data-cy="cardId"
              :class="{ valid: !$v.topboxes.cardId.$invalid, invalid: $v.topboxes.cardId.$invalid }"
              v-model="$v.topboxes.cardId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.topboxes.auxiliary')" for="topboxes-auxiliary">Auxiliary</label>
            <input
              type="text"
              class="form-control"
              name="auxiliary"
              id="topboxes-auxiliary"
              data-cy="auxiliary"
              :class="{ valid: !$v.topboxes.auxiliary.$invalid, invalid: $v.topboxes.auxiliary.$invalid }"
              v-model="$v.topboxes.auxiliary.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.topboxes.open')" for="topboxes-open">Open</label>
            <input
              type="checkbox"
              class="form-check"
              name="open"
              id="topboxes-open"
              data-cy="open"
              :class="{ valid: !$v.topboxes.open.$invalid, invalid: $v.topboxes.open.$invalid }"
              v-model="$v.topboxes.open.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.topboxes.conversation')" for="topboxes-conversation">Conversation</label>
            <select
              class="form-control"
              id="topboxes-conversation"
              data-cy="conversation"
              name="conversation"
              v-model="topboxes.conversation"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  topboxes.conversation && conversationOption.id === topboxes.conversation.id ? topboxes.conversation : conversationOption
                "
                v-for="conversationOption in conversations"
                :key="conversationOption.id"
              >
                {{ conversationOption.id }}
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
            :disabled="$v.topboxes.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./topboxes-update.component.ts"></script>
