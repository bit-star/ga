/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ApiClientUpdateComponent from '@/entities/api-client/api-client-update.vue';
import ApiClientClass from '@/entities/api-client/api-client-update.component';
import ApiClientService from '@/entities/api-client/api-client.service';

import LinkSystemService from '@/entities/link-system/link-system.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('ApiClient Management Update Component', () => {
    let wrapper: Wrapper<ApiClientClass>;
    let comp: ApiClientClass;
    let apiClientServiceStub: SinonStubbedInstance<ApiClientService>;

    beforeEach(() => {
      apiClientServiceStub = sinon.createStubInstance<ApiClientService>(ApiClientService);

      wrapper = shallowMount<ApiClientClass>(ApiClientUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          apiClientService: () => apiClientServiceStub,

          linkSystemService: () => new LinkSystemService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.apiClient = entity;
        apiClientServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiClientServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.apiClient = entity;
        apiClientServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiClientServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApiClient = { id: 123 };
        apiClientServiceStub.find.resolves(foundApiClient);
        apiClientServiceStub.retrieve.resolves([foundApiClient]);

        // WHEN
        comp.beforeRouteEnter({ params: { apiClientId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.apiClient).toBe(foundApiClient);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
