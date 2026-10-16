import './generate-icons.mjs';
import {mkdir,copyFile} from 'node:fs/promises';
import {fileURLToPath} from 'node:url';
import path from 'node:path';
const root=path.resolve(path.dirname(fileURLToPath(import.meta.url)),'..');
const files=['index.html','styles.css','app.js','manifest.webmanifest','sw.js','icons/icon.svg','icons/icon-192.png','icons/icon-512.png'];
for(const f of files){const target=path.join(root,'android/app/src/main/assets',f);await mkdir(path.dirname(target),{recursive:true});await copyFile(path.join(root,f),target)}
console.log(`Synced ${files.length} web assets into Android.`);
