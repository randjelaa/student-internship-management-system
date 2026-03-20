import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormArray, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CvService } from './cv.service';
import { AuthService } from '../../core/auth/auth.service';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cv',
  standalone: true,
  imports: [ReactiveFormsModule, MatInputModule, MatButtonModule],
  templateUrl: './cv.component.html',
  styleUrls: ['./cv.component.css']
})
export class CvComponent implements OnInit {

  studentId!: number;
  hasCv = false;
  imagePreview: string | null = null;

  form = this.fb.group({
    photoUrl: [''],
    summary: [''],

    educations: this.fb.array<FormGroup>([]),
    experiences: this.fb.array<FormGroup>([]),
    skills: this.fb.array<FormGroup>([]),
    languages: this.fb.array<FormGroup>([]),
  interests: this.fb.array<FormGroup>([])
  });

  constructor(
    private fb: FormBuilder,
    private cvService: CvService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    const user = this.authService.getUser();
    if (!user) {
      this.router.navigate(['/login']);
      return;
    }
    this.studentId = user.id;
    this.loadCv();
  }

  // ===== GETTERS =====
  get educations(): FormArray<FormGroup> {
    return this.form.get('educations') as FormArray<FormGroup>;
  }

  get experiences(): FormArray<FormGroup> {
    return this.form.get('experiences') as FormArray<FormGroup>;
  }

  get skills(): FormArray<FormGroup> {
    return this.form.get('skills') as FormArray<FormGroup>;
  }

  get languages(): FormArray<FormGroup> {
  return this.form.get('languages') as FormArray<FormGroup>;
}

get interests(): FormArray<FormGroup> {
  return this.form.get('interests') as FormArray<FormGroup>;
}

  // ===== LOAD CV =====
  loadCv() {
    this.cvService.getCv(this.studentId).subscribe({
      next: (cv) => {
        this.imagePreview = cv?.photoUrl || null;
        this.clearArrays();

        this.hasCv = !!cv;

        if (!cv) {
          this.addEducation();
          this.addExperience();
          this.addSkill();
          return;
        }

        this.form.patchValue({
          photoUrl: cv.photoUrl,
          summary: cv.summary
        });

        // EDUCATION
        // EDUCATION
        cv.educations.forEach(e => this.educations.push(this.fb.group({
          id: [e.id],
          institution: [e.institution],
          degree: [e.degree],
          fieldOfStudy: [e.fieldOfStudy],
          startYear: [e.startYear],
          endYear: [e.endYear]
        }) as FormGroup));

        // EXPERIENCE
        cv.experiences.forEach(e => this.experiences.push(this.fb.group({
          id: [e.id],
          companyName: [e.companyName],
          position: [e.position],
          description: [e.description],
          startDate: [e.startDate],
          endDate: [e.endDate]
        }) as FormGroup));

        // SKILLS
        cv.skills.forEach(s => this.skills.push(this.fb.group({
          id: [s.id],
          skillName: [s.skillName],
          skillLevel: [s.skillLevel]
        }) as FormGroup));

        cv.languages.forEach(l => this.languages.push(this.fb.group({
  id: [l.id],
  languageName: [l.languageName],
  level: [l.level]
}) as FormGroup));

cv.interests.forEach(i => this.interests.push(this.fb.group({
  id: [i.id],
  interestName: [i.interestName]
}) as FormGroup));
      },
      error: () => {
        this.hasCv = false;
        this.addEducation();
        this.addExperience();
        this.addSkill();
      }
    });
  }

  clearArrays() {
    this.educations.clear();
    this.experiences.clear();
    this.skills.clear();
  }

  // ===== ADD METHODS =====
  addEducation() {
  this.educations.push(this.fb.group({
    id: [null],
    institution: [''],
    degree: [''],
    fieldOfStudy: [''],
    startYear: [''],
    endYear: ['']
  }) as FormGroup);
}

  addExperience() {
  this.experiences.push(this.fb.group({
    id: [null],
    companyName: [''],
    position: [''],
    description: [''],
    startDate: [''],
    endDate: ['']
  }) as FormGroup);
}

  addSkill() {
  this.skills.push(this.fb.group({
    id: [null],
    skillName: [''],
    skillLevel: ['']
  }) as FormGroup);
}

  removeEducation(i: number) {
    this.educations.removeAt(i);
  }

  removeExperience(i: number) {
    this.experiences.removeAt(i);
  }

  removeSkill(i: number) {
    this.skills.removeAt(i);
  }

  addLanguage() {
  this.languages.push(this.fb.group({
    id: [null],
    languageName: [''],
    level: ['']
  }) as FormGroup);
}

removeLanguage(i: number) {
  this.languages.removeAt(i);
}

addInterest() {
  this.interests.push(this.fb.group({
    id: [null],
    interestName: ['']
  }) as FormGroup);
}

removeInterest(i: number) {
  this.interests.removeAt(i);
}

  // ===== SAVE (CREATE + UPDATE) =====
  save() {
  const formValue = this.form.value;

  const body = {
    photoUrl: formValue.photoUrl,
    summary: formValue.summary,

    // SAMO ID-evi postojećih stavki
    educationIds: formValue.educations?.filter(e => e.id).map(e => e.id),
    experienceIds: formValue.experiences?.filter(e => e.id).map(e => e.id),
    skillIds: formValue.skills?.filter(s => s.id).map(s => s.id),
    languageIds: formValue.languages?.filter(l => l.id).map(l => l.id),
    interestIds: formValue.interests?.filter(i => i.id).map(i => i.id),

    // NOVI objekti sa detaljima
    newEducations: formValue.educations?.filter(e => !e.id).map(e => ({
      institution: e.institution,
      degree: e.degree,
      fieldOfStudy: e.fieldOfStudy,
      startYear: e.startYear,
      endYear: e.endYear
    })),

    newExperiences: formValue.experiences?.filter(e => !e.id).map(e => ({
      companyName: e.companyName,
      position: e.position,
      description: e.description,
      startDate: e.startDate,
      endDate: e.endDate
    })),

    newSkills: formValue.skills?.filter(s => !s.id).map(s => ({
      skillName: s.skillName,
      skillLevel: s.skillLevel
    })),

    newLanguages: formValue.languages?.filter(l => !l.id).map(l => ({
      languageName: l.languageName,
      level: l.level
    })),

    newInterests: formValue.interests?.filter(i => !i.id).map(i => ({
      interestName: i.interestName
    }))
  };

  const request = this.hasCv
    ? this.cvService.updateCv(this.studentId, body)
    : this.cvService.createCv(this.studentId, body);

  request.subscribe({
    next: () => {
      alert(this.hasCv ? 'Updated!' : 'Created!');
      this.loadCv();
    },
    error: () => alert('Error saving CV')
  });
}

  // ===== DELETE =====
  deleteCv() {
    this.cvService.deleteCv(this.studentId).subscribe({
      next: () => {
        alert('Deleted!');
        this.hasCv = false;
        this.form.reset();
        this.clearArrays();
        this.imagePreview = null; // 👈 DODAJ OVO
        this.addEducation();
        this.addExperience();
        this.addSkill();
      },
      error: () => alert('Error deleting CV')
    });
  }

  // ===== DOWNLOAD PDF =====
  downloadPdf() {
    this.cvService.downloadPdf(this.studentId).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      window.open(url);
    });
  }

  onFileSelected(event: any) {
  const file = event.target.files[0];
  if (!file) return;

  // preview (da odmah vidi sliku)
  const reader = new FileReader();
  reader.onload = () => {
    this.imagePreview = reader.result as string;
  };
  reader.readAsDataURL(file);

  // upload na backend
  const formData = new FormData();
  formData.append('file', file);

  this.cvService.uploadImage(this.studentId, formData)
    .subscribe({
      next: (url: string) => {
        this.form.patchValue({
          photoUrl: url
        });
      },
      error: () => alert('Upload failed')
    });
}

removePhoto() {
  this.imagePreview = null;
  this.form.patchValue({
    photoUrl: null
  });
}
}