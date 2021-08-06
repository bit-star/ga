<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.publicCardData.home.createOrEditLabel"
          data-cy="PublicCardDataCreateUpdateHeading"
          v-text="$t('gaApp.publicCardData.home.createOrEditLabel')"
        >
          Create or edit a PublicCardData
        </h2>
        <div>
          <div class="form-group" v-if="publicCardData.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="publicCardData.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.name')" for="public-card-data-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="public-card-data-name"
              data-cy="name"
              :class="{ valid: !$v.publicCardData.name.$invalid, invalid: $v.publicCardData.name.$invalid }"
              v-model="$v.publicCardData.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.fee')" for="public-card-data-fee">Fee</label>
            <input
              type="text"
              class="form-control"
              name="fee"
              id="public-card-data-fee"
              data-cy="fee"
              :class="{ valid: !$v.publicCardData.fee.$invalid, invalid: $v.publicCardData.fee.$invalid }"
              v-model="$v.publicCardData.fee.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.reason')" for="public-card-data-reason">Reason</label>
            <input
              type="text"
              class="form-control"
              name="reason"
              id="public-card-data-reason"
              data-cy="reason"
              :class="{ valid: !$v.publicCardData.reason.$invalid, invalid: $v.publicCardData.reason.$invalid }"
              v-model="$v.publicCardData.reason.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.itemType')" for="public-card-data-itemType">Item Type</label>
            <input
              type="text"
              class="form-control"
              name="itemType"
              id="public-card-data-itemType"
              data-cy="itemType"
              :class="{ valid: !$v.publicCardData.itemType.$invalid, invalid: $v.publicCardData.itemType.$invalid }"
              v-model="$v.publicCardData.itemType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.typesOfFee')" for="public-card-data-typesOfFee"
              >Types Of Fee</label
            >
            <input
              type="text"
              class="form-control"
              name="typesOfFee"
              id="public-card-data-typesOfFee"
              data-cy="typesOfFee"
              :class="{ valid: !$v.publicCardData.typesOfFee.$invalid, invalid: $v.publicCardData.typesOfFee.$invalid }"
              v-model="$v.publicCardData.typesOfFee.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.agree')" for="public-card-data-agree">Agree</label>
            <input
              type="checkbox"
              class="form-check"
              name="agree"
              id="public-card-data-agree"
              data-cy="agree"
              :class="{ valid: !$v.publicCardData.agree.$invalid, invalid: $v.publicCardData.agree.$invalid }"
              v-model="$v.publicCardData.agree.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.requestid')" for="public-card-data-requestid"
              >Requestid</label
            >
            <input
              type="number"
              class="form-control"
              name="requestid"
              id="public-card-data-requestid"
              data-cy="requestid"
              :class="{ valid: !$v.publicCardData.requestid.$invalid, invalid: $v.publicCardData.requestid.$invalid }"
              v-model.number="$v.publicCardData.requestid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.workflowid')" for="public-card-data-workflowid"
              >Workflowid</label
            >
            <input
              type="number"
              class="form-control"
              name="workflowid"
              id="public-card-data-workflowid"
              data-cy="workflowid"
              :class="{ valid: !$v.publicCardData.workflowid.$invalid, invalid: $v.publicCardData.workflowid.$invalid }"
              v-model.number="$v.publicCardData.workflowid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.valid')" for="public-card-data-valid">Valid</label>
            <input
              type="checkbox"
              class="form-check"
              name="valid"
              id="public-card-data-valid"
              data-cy="valid"
              :class="{ valid: !$v.publicCardData.valid.$invalid, invalid: $v.publicCardData.valid.$invalid }"
              v-model="$v.publicCardData.valid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.conversation')" for="public-card-data-conversation"
              >Conversation</label
            >
            <select
              class="form-control"
              id="public-card-data-conversation"
              data-cy="conversation"
              name="conversation"
              v-model="publicCardData.conversation"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  publicCardData.conversation && conversationOption.id === publicCardData.conversation.id
                    ? publicCardData.conversation
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
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.workflowTemplate')" for="public-card-data-workflowTemplate"
              >Workflow Template</label
            >
            <select
              class="form-control"
              id="public-card-data-workflowTemplate"
              data-cy="workflowTemplate"
              name="workflowTemplate"
              v-model="publicCardData.workflowTemplate"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  publicCardData.workflowTemplate && workflowTemplateOption.id === publicCardData.workflowTemplate.id
                    ? publicCardData.workflowTemplate
                    : workflowTemplateOption
                "
                v-for="workflowTemplateOption in workflowTemplates"
                :key="workflowTemplateOption.id"
              >
                {{ workflowTemplateOption.id }}
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
            :disabled="$v.publicCardData.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./public-card-data-update.component.ts"></script>
