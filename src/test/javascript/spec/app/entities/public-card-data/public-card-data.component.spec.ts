/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PublicCardDataComponent from '@/entities/public-card-data/public-card-data.vue';
import PublicCardDataClass from '@/entities/public-card-data/public-card-data.component';
import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';

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
  describe('PublicCardData Management Component', () => {
    let wrapper: Wrapper<PublicCardDataClass>;
    let comp: PublicCardDataClass;
    let publicCardDataServiceStub: SinonStubbedInstance<PublicCardDataService>;

    beforeEach(() => {
      publicCardDataServiceStub = sinon.createStubInstance<PublicCardDataService>(PublicCardDataService);
      publicCardDataServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PublicCardDataClass>(PublicCardDataComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          publicCardDataService: () => publicCardDataServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      publicCardDataServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPublicCardDatas();
      await comp.$nextTick();

      // THEN
      expect(publicCardDataServiceStub.retrieve.called).toBeTruthy();
      expect(comp.publicCardData[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      publicCardDataServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePublicCardData();
      await comp.$nextTick();

      // THEN
      expect(publicCardDataServiceStub.delete.called).toBeTruthy();
      expect(publicCardDataServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
