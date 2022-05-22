import MdEditor from "md-editor-v3";
import "md-editor-v3/lib/style.css";

MdEditor.config({
  editorExtensions: {
    highlight: {
      css: {
        atomDark: {
          light:
            "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.5.1/styles/atom-one-dark.min.css",
          dark: "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.5.1/styles/atom-one-dark.min.css",
        },
      },
    },
  },
});

import { App } from "vue";
export function useMarkdown(app: App) {
  app.use(MdEditor);
}
