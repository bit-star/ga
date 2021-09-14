/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TopboxesComponent from '@/entities/topboxes/topboxes.vue';
import TopboxesClass from '@/entities/topboxes/topboxes.component';
import TopboxesService from '@/entities/topboxes/topboxes.service';

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
  describe('Topboxes Management Component', () => {
    let wrapper: Wrapper<TopboxesClass>;
    let comp: TopboxesClass;
    let topboxesServiceStub: SinonStubbedInstance<TopboxesService>;

    beforeEach(() => {
      topboxesServiceStub = sinon.createStubInstance<TopboxesService>(TopboxesService);
      topboxesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TopboxesClass>(TopboxesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          topboxesService: () => topboxesServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      topboxesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllTopboxess();
      await comp.$nextTick();

      // THEN
      expect(topboxesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.topboxes[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      topboxesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      comp.removeTopboxes();
      await comp.$nextTick();

      // THEN
      expect(topboxesServiceStub.delete.called).toBeTruthy();
      expect(topboxesServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
