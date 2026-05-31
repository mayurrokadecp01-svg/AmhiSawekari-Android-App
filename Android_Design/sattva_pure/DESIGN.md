# Design System Document

## 1. Overview & Creative North Star: "The Digital Sanctum"
This design system is envisioned as a **Digital Sanctum**—a serene, editorial-inspired space that transcends the cluttered utility of standard mobile apps. It is built to facilitate devotion and peace, moving away from "app-like" rigid grids toward a fluid, meditative experience.

**The Creative North Star** is "Intentional Breath." We break the "template" look by using exaggerated whitespace, asymmetrical content anchoring, and a hierarchy that treats text as sacred scripture rather than just data. This system rejects the industrial coldness of default frameworks in favor of a layered, warm, and high-end spiritual experience.

---

### 2. Colors: Tonal Devotion
The palette is rooted in the warmth of Saffron and Gold, balanced by the purity of White. We utilize a Material 3-inspired tonal scale to create depth without visual noise.

| Role | Token | Value | Intent |
| :--- | :--- | :--- | :--- |
| **Primary** | `primary` | `#8f4e00` | Deep Saffron for high-contrast meaningful actions. |
| **Accent** | `primary_container` | `#ff9933` | The "Soul" color. Used for hero backgrounds and key CTAs. |
| **Divine Glow**| `secondary_container`| `#fcd400` | Light Gold for moments of enlightenment or highlights. |
| **Surface** | `surface` | `#f9f9f9` | A soft, off-white to reduce eye strain during long reading. |
| **Layer 1** | `surface_container_low` | `#f3f3f3` | Subtle nesting for secondary content areas. |

#### The "No-Line" Rule
**Explicit Instruction:** Designers are prohibited from using 1px solid borders to section content. Boundaries must be defined solely through background color shifts. For example, a `surface_container_low` section should sit on a `surface` background to create a "ghost" boundary.

#### The "Glass & Gold" Rule
To move beyond a flat UI, use Glassmorphism for floating elements (Top App Bars, Bottom Navigation). Use semi-transparent surface colors with a `backdrop-blur` of 20px-40px. This allows the saffron watermark or background content to bleed through softly, maintaining a sense of continuity.

---

### 3. Typography: Editorial Clarity
The typography is split by platform to feel "native yet bespoke." We use **Plus Jakarta Sans** for high-end display moments to elevate the spiritual aesthetic beyond system defaults.

*   **Display (Plus Jakarta Sans):** Used for spiritual quotes or chapter headings. Large, airy, and centered.
*   **Headlines (Plus Jakarta Sans):** Bold, authoritative, yet approachable.
*   **Body (Inter/SF Pro/Roboto):** Optimized for long-form reading of stotras or news. High line-height (1.6x) is mandatory to ensure a "peaceful" reading rhythm.

| Scale | Font | Size | Weight | Tracking |
| :--- | :--- | :--- | :--- | :--- |
| **Display-Lg** | Plus Jakarta Sans | 3.5rem | 600 | -0.02em |
| **Headline-Md** | Plus Jakarta Sans | 1.75rem | 500 | -0.01em |
| **Title-Md** | Inter / Native | 1.125rem | 500 | 0 |
| **Body-Lg** | Inter / Native | 1rem | 400 | 0.01em |

---

### 4. Elevation & Depth: Tonal Layering
We reject traditional drop shadows. Depth in this system is achieved through **Tonal Layering** and **Ambient Glows.**

*   **The Layering Principle:** Stack `surface-container-lowest` cards on a `surface-container-low` background. This creates a soft, natural lift mimicking fine handmade paper.
*   **Ambient Shadows:** If a "Floating Action Button" or "Modal" requires a shadow, use a diffusion value of 30px-50px with an opacity of 4%-6%. The shadow color must be a tinted Saffron (`#8f4e00` at 5% opacity) rather than grey, ensuring the shadow feels like a "warm glow" on the surface.
*   **The "Ghost Border" Fallback:** If a container needs more definition, use the `outline_variant` (#dbc2b0) at **15% opacity**. Never use a 100% opaque border.

---

### 5. Components: Modern Minimalist Primitives

#### Buttons
*   **Primary:** Rounded `full` (9999px). Transition from `primary` (#8f4e00) to `primary_container` (#ff9933) as a subtle vertical gradient. This adds "soul" and dimension.
*   **Secondary:** Ghost style. No background, `on_surface` text, with a 15% opacity `outline_variant`.

#### Cards & Lists
*   **Card Aesthetic:** Radius `xl` (1.5rem). Use the **Watermark Logo Background** at 3% opacity, anchored to the bottom-right of the card to provide a signature branded feel.
*   **No Dividers:** Forbid the use of horizontal lines. Use `spacing-6` (2rem) of vertical whitespace or a subtle shift to `surface_container_highest` to separate list items.

#### Input Fields
*   **Minimalist Entry:** Bottom-line only or fully filled `surface_container_low` with `xl` (1.5rem) rounded corners. The focus state should be a soft `primary_container` glow, not a harsh border.

#### Signature Component: The "Peace Header"
A hero component that uses a large `Display-Lg` title with an asymmetrical "Saffron Glow" (a large, blurred radial gradient) behind it, creating a feeling of dawn or spiritual awakening.

---

### 6. Do’s and Don’ts

#### Do
*   **Use Asymmetry:** Place headings to the left and body text with a wider right margin to create an editorial, high-end look.
*   **Embrace Whitespace:** If you think there is enough space, add 20% more. Space is the "silence" in this spiritual experience.
*   **Native Continuity:** Use Material 3 logic for Android and HIG logic for iOS (e.g., San Francisco for labels) to ensure users feel "at home" in their OS while immersed in the brand.

#### Don’t
*   **No Pure Black:** Never use `#000000`. Use `on_surface` (#1a1c1c) for text to keep the contrast soft and readable.
*   **No Sharp Corners:** Avoid any `none` or `sm` roundedness unless it's a 1px detail. Spirituality is fluid; the UI should be too.
*   **No Industrial Grids:** Avoid perfectly symmetrical 2x2 grids for icons. Try staggered lists or varying card heights to feel more organic.

---

### 7. Spacing Scale (Reference)
Use these tokens to maintain a rhythmic "breath" throughout the UI.
*   **Micro (1):** 0.35rem — For icon-to-label spacing.
*   **Standard (3):** 1rem — For internal padding.
*   **Breath (6):** 2rem — For section spacing.
*   **Sanctum (10):** 3.5rem — For top-of-page hero offsets.