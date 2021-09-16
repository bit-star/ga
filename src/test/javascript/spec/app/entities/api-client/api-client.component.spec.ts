/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ApiClientComponent from '@/entities/api-client/api-client.vue';
import ApiClientClass from '@/entities/api-client/api-client.component';
import ApiClientService from '@/entities/api-client/api-client.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('ApiClient Management Component', () => {
    let wrapper: Wrapper<ApiClientClass>;
    let comp: ApiClientClass;
    let apiClientServiceStub: SinonStubbedInstance<ApiClientService>;

    beforeEach(() => {
      apiClientServiceStub = sinon.createStubInstance<ApiClientService>(ApiClientService);
      apiClientServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ApiClientClass>(ApiClientComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          apiClientService: () => apiClientServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      apiClientServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllApiClients();
      await comp.$nextTick();

      // THEN
      expect(apiClientServiceStub.retrieve.called).toBeTruthy();
      expect(comp.apiClients[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      apiClientServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeApiClient();
      await comp.$nextTick();

      // THEN
      expect(apiClientServiceStub.delete.called).toBeTruthy();
      expect(apiClientServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
