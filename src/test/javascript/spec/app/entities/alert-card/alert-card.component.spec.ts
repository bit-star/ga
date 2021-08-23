/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AlertCardComponent from '@/entities/alert-card/alert-card.vue';
import AlertCardClass from '@/entities/alert-card/alert-card.component';
import AlertCardService from '@/entities/alert-card/alert-card.service';

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
  describe('AlertCard Management Component', () => {
    let wrapper: Wrapper<AlertCardClass>;
    let comp: AlertCardClass;
    let alertCardServiceStub: SinonStubbedInstance<AlertCardService>;

    beforeEach(() => {
      alertCardServiceStub = sinon.createStubInstance<AlertCardService>(AlertCardService);
      alertCardServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AlertCardClass>(AlertCardComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertCardService: () => alertCardServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      alertCardServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAlertCards();
      await comp.$nextTick();

      // THEN
      expect(alertCardServiceStub.retrieve.called).toBeTruthy();
      expect(comp.alertCards[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      alertCardServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAlertCard();
      await comp.$nextTick();

      // THEN
      expect(alertCardServiceStub.delete.called).toBeTruthy();
      expect(alertCardServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
