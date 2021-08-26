<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.alertCard.home.createOrEditLabel"
          data-cy="AlertCardCreateUpdateHeading"
          v-text="$t('gaApp.alertCard.home.createOrEditLabel')"
        >
          Create or edit a AlertCard
        </h2>
        <div>
          <div class="form-group" v-if="alertCard.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="alertCard.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.alertCard.text')" for="alert-card-text">Text</label>
            <input
              type="text"
              class="form-control"
              name="text"
              id="alert-card-text"
              data-cy="text"
              :class="{ valid: !$v.alertCard.text.$invalid, invalid: $v.alertCard.text.$invalid }"
              v-model="$v.alertCard.text.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.alertCard.userId')" for="alert-card-userId">User Id</label>
            <input
              type="text"
              class="form-control"
              name="userId"
              id="alert-card-userId"
              data-cy="userId"
              :class="{ valid: !$v.alertCard.userId.$invalid, invalid: $v.alertCard.userId.$invalid }"
              v-model="$v.alertCard.userId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.alertCard.link')" for="alert-card-link">Link</label>
            <input
              type="text"
              class="form-control"
              name="link"
              id="alert-card-link"
              data-cy="link"
              :class="{ valid: !$v.alertCard.link.$invalid, invalid: $v.alertCard.link.$invalid }"
              v-model="$v.alertCard.link.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.alertCard.md1')" for="alert-card-md1">Md 1</label>
            <input
              type="text"
              class="form-control"
              name="md1"
              id="alert-card-md1"
              data-cy="md1"
              :class="{ valid: !$v.alertCard.md1.$invalid, invalid: $v.alertCard.md1.$invalid }"
              v-model="$v.alertCard.md1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.alertCard.publicCardData')" for="alert-card-publicCardData"
              >Public Card Data</label
            >
            <select
              class="form-control"
              id="alert-card-publicCardData"
              data-cy="publicCardData"
              name="publicCardData"
              v-model="alertCard.publicCardData"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  alertCard.publicCardData && publicCardDataOption.id === alertCard.publicCardData.id
                    ? alertCard.publicCardData
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
            :disabled="$v.alertCard.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./alert-card-update.component.ts"></script>
