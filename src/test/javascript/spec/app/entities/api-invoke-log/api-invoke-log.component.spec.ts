/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ApiInvokeLogComponent from '@/entities/api-invoke-log/api-invoke-log.vue';
import ApiInvokeLogClass from '@/entities/api-invoke-log/api-invoke-log.component';
import ApiInvokeLogService from '@/entities/api-invoke-log/api-invoke-log.service';

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
  describe('ApiInvokeLog Management Component', () => {
    let wrapper: Wrapper<ApiInvokeLogClass>;
    let comp: ApiInvokeLogClass;
    let apiInvokeLogServiceStub: SinonStubbedInstance<ApiInvokeLogService>;

    beforeEach(() => {
      apiInvokeLogServiceStub = sinon.createStubInstance<ApiInvokeLogService>(ApiInvokeLogService);
      apiInvokeLogServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ApiInvokeLogClass>(ApiInvokeLogComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          apiInvokeLogService: () => apiInvokeLogServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      apiInvokeLogServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllApiInvokeLogs();
      await comp.$nextTick();

      // THEN
      expect(apiInvokeLogServiceStub.retrieve.called).toBeTruthy();
      expect(comp.apiInvokeLogs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      apiInvokeLogServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeApiInvokeLog();
      await comp.$nextTick();

      // THEN
      expect(apiInvokeLogServiceStub.delete.called).toBeTruthy();
      expect(apiInvokeLogServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
