<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.confirmCard.home.createOrEditLabel"
          data-cy="ConfirmCardCreateUpdateHeading"
          v-text="$t('gaApp.confirmCard.home.createOrEditLabel')"
        >
          Create or edit a ConfirmCard
        </h2>
        <div>
          <div class="form-group" v-if="confirmCard.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="confirmCard.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.confirmCard.text')" for="confirm-card-text">Text</label>
            <input
              type="text"
              class="form-control"
              name="text"
              id="confirm-card-text"
              data-cy="text"
              :class="{ valid: !$v.confirmCard.text.$invalid, invalid: $v.confirmCard.text.$invalid }"
              v-model="$v.confirmCard.text.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.confirmCard.finish')" for="confirm-card-finish">Finish</label>
            <input
              type="text"
              class="form-control"
              name="finish"
              id="confirm-card-finish"
              data-cy="finish"
              :class="{ valid: !$v.confirmCard.finish.$invalid, invalid: $v.confirmCard.finish.$invalid }"
              v-model="$v.confirmCard.finish.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.confirmCard.userId')" for="confirm-card-userId">User Id</label>
            <input
              type="text"
              class="form-control"
              name="userId"
              id="confirm-card-userId"
              data-cy="userId"
              :class="{ valid: !$v.confirmCard.userId.$invalid, invalid: $v.confirmCard.userId.$invalid }"
              v-model="$v.confirmCard.userId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.confirmCard.link')" for="confirm-card-link">Link</label>
            <input
              type="text"
              class="form-control"
              name="link"
              id="confirm-card-link"
              data-cy="link"
              :class="{ valid: !$v.confirmCard.link.$invalid, invalid: $v.confirmCard.link.$invalid }"
              v-model="$v.confirmCard.link.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.confirmCard.md1')" for="confirm-card-md1">Md 1</label>
            <input
              type="text"
              class="form-control"
              name="md1"
              id="confirm-card-md1"
              data-cy="md1"
              :class="{ valid: !$v.confirmCard.md1.$invalid, invalid: $v.confirmCard.md1.$invalid }"
              v-model="$v.confirmCard.md1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.confirmCard.publicCardData')" for="confirm-card-publicCardData"
              >Public Card Data</label
            >
            <select
              class="form-control"
              id="confirm-card-publicCardData"
              data-cy="publicCardData"
              name="publicCardData"
              v-model="confirmCard.publicCardData"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  confirmCard.publicCardData && publicCardDataOption.id === confirmCard.publicCardData.id
                    ? confirmCard.publicCardData
                    : publicCardDataOption
                "
                v-for="publicCardDataOption in publicCardData"
                :key="publicCardDataOption.id"
              >
                {{ publicCardDataOption.id }}
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
            :disabled="$v.confirmCard.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./confirm-card-update.component.ts"></script>
