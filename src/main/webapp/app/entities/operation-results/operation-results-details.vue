<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="operationResults">
        <h2 class="jh-entity-heading" data-cy="operationResultsDetailsHeading">
          <span v-text="$t('gaApp.operationResults.detail.title')">OperationResults</span> {{ operationResults.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.operationResults.operationType')">Operation Type</span>
          </dt>
          <dd>
            <span v-text="$t('gaApp.OperationType.' + operationResults.operationType)">{{ operationResults.operationType }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.operationResults.time')">Time</span>
          </dt>
          <dd>
            <span v-if="operationResults.time">{{ $d(Date.parse(operationResults.time), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.operationResults.text')">Text</span>
          </dt>
          <dd>
            <span>{{ operationResults.text }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.operationResults.ddUser')">Dd User</span>
          </dt>
          <dd>
            <div v-if="operationResults.ddUser">
              <router-link :to="{ name: 'DdUserView', params: { ddUserId: operationResults.ddUser.id } }">{{
                operationResults.ddUser.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('gaApp.operationResults.publicCardData')">Public Card Data</span>
          </dt>
          <dd>
            <div v-if="operationResults.publicCardData">
              <router-link :to="{ name: 'PublicCardDataView', params: { publicCardDataId: operationResults.publicCardData.id } }">{{
                operationResults.publicCardData.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="operationResults.id"
          :to="{ name: 'OperationResultsEdit', params: { operationResultsId: operationResults.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./operation-results-details.component.ts"></script>
