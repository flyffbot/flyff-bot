import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import en from './locales/en.json';
i18n
  .use(initReactI18next)
  // init i18next
  // for all options read: https://www.i18next.com/overview/configuration-options
  .init({
    resources: {
      en: {
        translation: en
      }
    },
    lng: "en", // if you're using a language detector, do not define the lng option
    fallbackLng: 'en',
    debug: true,

    interpolation: {
      escapeValue: false, // not needed for react as it escapes by default
    }
  });


export default i18n;
