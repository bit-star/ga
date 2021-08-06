<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="linkSystem">
        <h2 class="jh-entity-heading" data-cy="linkSystemDetailsHeading">
          <span v-text="$t('gaApp.linkSystem.detail.title')">LinkSystem</span> {{ linkSystem.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.linkSystem.name')">Name</span>
          </dt>
          <dd>
            <span>{{ linkSystem.name }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="linkSystem.id"
          :to="{ name: 'LinkSystemEdit', params: { linkSystemId: linkSystem.id } }"
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

<script lang="ts" src="./link-system-details.component.ts"></script>
