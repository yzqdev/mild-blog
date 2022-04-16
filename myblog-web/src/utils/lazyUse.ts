import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { App  } from "vue";
export function useMarkdown(app:App ){



    app.use(MdEditor);
}
