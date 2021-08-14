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
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.link')" for="public-card-data-link">Link</label>
            <input
              type="text"
              class="form-control"
              name="link"
              id="public-card-data-link"
              data-cy="link"
              :class="{ valid: !$v.publicCardData.link.$invalid, invalid: $v.publicCardData.link.$invalid }"
              v-model="$v.publicCardData.link.$model"
            />
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
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.feeValue')" for="public-card-data-feeValue">Fee Value</label>
            <input
              type="text"
              class="form-control"
              name="feeValue"
              id="public-card-data-feeValue"
              data-cy="feeValue"
              :class="{ valid: !$v.publicCardData.feeValue.$invalid, invalid: $v.publicCardData.feeValue.$invalid }"
              v-model="$v.publicCardData.feeValue.$model"
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
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.finish')" for="public-card-data-finish">Finish</label>
            <input
              type="text"
              class="form-control"
              name="finish"
              id="public-card-data-finish"
              data-cy="finish"
              :class="{ valid: !$v.publicCardData.finish.$invalid, invalid: $v.publicCardData.finish.$invalid }"
              v-model="$v.publicCardData.finish.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.status')" for="public-card-data-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.publicCardData.status.$invalid, invalid: $v.publicCardData.status.$invalid }"
              v-model="$v.publicCardData.status.$model"
              id="public-card-data-status"
              data-cy="status"
            >
              <option value="Effect" v-bind:label="$t('gaApp.PublicDataCardStatus.Effect')">Effect</option>
              <option value="Invalid" v-bind:label="$t('gaApp.PublicDataCardStatus.Invalid')">Invalid</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.content')" for="public-card-data-content">Content</label>
            <input
              type="text"
              class="form-control"
              name="content"
              id="public-card-data-content"
              data-cy="content"
              :class="{ valid: !$v.publicCardData.content.$invalid, invalid: $v.publicCardData.content.$invalid }"
              v-model="$v.publicCardData.content.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.agreeNum')" for="public-card-data-agreeNum">Agree Num</label>
            <input
              type="number"
              class="form-control"
              name="agreeNum"
              id="public-card-data-agreeNum"
              data-cy="agreeNum"
              :class="{ valid: !$v.publicCardData.agreeNum.$invalid, invalid: $v.publicCardData.agreeNum.$invalid }"
              v-model.number="$v.publicCardData.agreeNum.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.refuseNum')" for="public-card-data-refuseNum"
              >Refuse Num</label
            >
            <input
              type="number"
              class="form-control"
              name="refuseNum"
              id="public-card-data-refuseNum"
              data-cy="refuseNum"
              :class="{ valid: !$v.publicCardData.refuseNum.$invalid, invalid: $v.publicCardData.refuseNum.$invalid }"
              v-model.number="$v.publicCardData.refuseNum.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.oaStatus')" for="public-card-data-oaStatus">Oa Status</label>
            <select
              class="form-control"
              name="oaStatus"
              :class="{ valid: !$v.publicCardData.oaStatus.$invalid, invalid: $v.publicCardData.oaStatus.$invalid }"
              v-model="$v.publicCardData.oaStatus.$model"
              id="public-card-data-oaStatus"
              data-cy="oaStatus"
            >
              <option value="Launch" v-bind:label="$t('gaApp.WorkflowInstanceStatus.Launch')">Launch</option>
              <option value="Refuse" v-bind:label="$t('gaApp.WorkflowInstanceStatus.Refuse')">Refuse</option>
              <option value="Agree" v-bind:label="$t('gaApp.WorkflowInstanceStatus.Agree')">Agree</option>
              <option value="Archive" v-bind:label="$t('gaApp.WorkflowInstanceStatus.Archive')">Archive</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.publicCardData.workflowInstance')" for="public-card-data-workflowInstance"
              >Workflow Instance</label
            >
            <select
              class="form-control"
              id="public-card-data-workflowInstance"
              data-cy="workflowInstance"
              name="workflowInstance"
              v-model="publicCardData.workflowInstance"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  publicCardData.workflowInstance && workflowInstanceOption.id === publicCardData.workflowInstance.id
                    ? publicCardData.workflowInstance
                    : workflowInstanceOption
                "
                v-for="workflowInstanceOption in workflowInstances"
                :key="workflowInstanceOption.id"
              >
                {{ workflowInstanceOption.id }}
              </option>
            </select>
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
