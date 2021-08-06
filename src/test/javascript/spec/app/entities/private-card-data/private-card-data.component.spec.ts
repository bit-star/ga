/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PrivateCardDataComponent from '@/entities/private-card-data/private-card-data.vue';
import PrivateCardDataClass from '@/entities/private-card-data/private-card-data.component';
import PrivateCardDataService from '@/entities/private-card-data/private-card-data.service';

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
  describe('PrivateCardData Management Component', () => {
    let wrapper: Wrapper<PrivateCardDataClass>;
    let comp: PrivateCardDataClass;
    let privateCardDataServiceStub: SinonStubbedInstance<PrivateCardDataService>;

    beforeEach(() => {
      privateCardDataServiceStub = sinon.createStubInstance<PrivateCardDataService>(PrivateCardDataService);
      privateCardDataServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PrivateCardDataClass>(PrivateCardDataComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          privateCardDataService: () => privateCardDataServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      privateCardDataServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPrivateCardDatas();
      await comp.$nextTick();

      // THEN
      expect(privateCardDataServiceStub.retrieve.called).toBeTruthy();
      expect(comp.privateCardData[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      privateCardDataServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePrivateCardData();
      await comp.$nextTick();

      // THEN
      expect(privateCardDataServiceStub.delete.called).toBeTruthy();
      expect(privateCardDataServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
