/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ApiClientDetailComponent from '@/entities/api-client/api-client-details.vue';
import ApiClientClass from '@/entities/api-client/api-client-details.component';
import ApiClientService from '@/entities/api-client/api-client.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ApiClient Management Detail Component', () => {
    let wrapper: Wrapper<ApiClientClass>;
    let comp: ApiClientClass;
    let apiClientServiceStub: SinonStubbedInstance<ApiClientService>;

    beforeEach(() => {
      apiClientServiceStub = sinon.createStubInstance<ApiClientService>(ApiClientService);

      wrapper = shallowMount<ApiClientClass>(ApiClientDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { apiClientService: () => apiClientServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundApiClient = { id: 123 };
        apiClientServiceStub.find.resolves(foundApiClient);

        // WHEN
        comp.retrieveApiClient(123);
        await comp.$nextTick();

        // THEN
        expect(comp.apiClient).toBe(foundApiClient);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApiClient = { id: 123 };
        apiClientServiceStub.find.resolves(foundApiClient);

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
